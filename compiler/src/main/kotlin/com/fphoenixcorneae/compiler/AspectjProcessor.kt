package com.fphoenixcorneae.compiler

import com.fphoenixcorneae.annotation.AndroidAspectj
import com.squareup.javapoet.*
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

/**
 * @desc：@AndroidAspectj 注解处理器
 * @date：2021-06-23 15:40
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class AspectjProcessor : AbstractProcessor() {
    private var mFiler: Filer? = null
    private var mElementUtils: Elements? = null
    private var mMessager: Messager? = null

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        mFiler = processingEnv.filer
        mElementUtils = processingEnv.elementUtils
        mMessager = processingEnv.messager
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val annotationTypes: MutableSet<String> = LinkedHashSet()
        annotationTypes.add(AndroidAspectj::class.java.canonicalName)
        return annotationTypes
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val aspectjElements = roundEnv.getElementsAnnotatedWith(AndroidAspectj::class.java)
        for (element in aspectjElements) {
            val classElement = element as TypeElement
            val packageElement = element.getEnclosingElement() as PackageElement
            val pointcutBuilder = AnnotationSpec.builder(ClassName.get("org.aspectj.lang.annotation", "Pointcut"))
            val annotation = element.getAnnotation(AndroidAspectj::class.java)
            val value = annotation.value
            if (value.isNotEmpty()) {
                pointcutBuilder.addMember("value", "\"" + value + "\"")
            } else {
                pointcutBuilder.addMember("value", "\"execution(@" + classElement.qualifiedName + " * *(..))\"").build()
            }
            val pointcut = pointcutBuilder.build()
            val pointcutMethod = MethodSpec.methodBuilder("pointcut" + classElement.simpleName)
                .addModifiers(Modifier.PUBLIC)
                .returns(Void.TYPE)
                .addAnnotation(pointcut)
                .build()
            val around = AnnotationSpec.builder(ClassName.get("org.aspectj.lang.annotation", "Around"))
                .addMember("value", "\"" + pointcutMethod.name + "()\"").build()
            val aroundMethod = MethodSpec.methodBuilder("around" + classElement.simpleName)
                .addModifiers(Modifier.PUBLIC)
                .returns(Void.TYPE)
                .addParameter(ClassName.get("org.aspectj.lang", "ProceedingJoinPoint"), "joinPoint")
                .addAnnotation(around)
                .addStatement("if (!(joinPoint.getSignature() instanceof \$T)){\n\tthrow new \$T(\"This annotation can only be used on Methods\");\n}",
                    ClassName.get("org.aspectj.lang.reflect", "MethodSignature"),
                    IllegalStateException::class.java)
                .addStatement("MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();")
                .addStatement(classElement.simpleName.toString() + " annotation = methodSignature.getMethod().getAnnotation(" + classElement.simpleName + ".class);")
                .addStatement("\$T.notifyHandler(" + classElement.simpleName + ".class, joinPoint)",
                    ClassName.get("com.fphoenixcorneae.aspectj", "AspectjHandler"))
                .build()
            val aspectj = TypeSpec.classBuilder(classElement.simpleName.toString() + "_Aspectj")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(pointcutMethod)
                .addMethod(aroundMethod)
                .addAnnotation(AnnotationSpec.builder(ClassName.get("org.aspectj.lang.annotation", "Aspect")).build())
                .build()
            val javaFile = JavaFile.builder(packageElement.qualifiedName.toString(), aspectj).build()
            try {
                javaFile.writeTo(mFiler)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return true
    }
}