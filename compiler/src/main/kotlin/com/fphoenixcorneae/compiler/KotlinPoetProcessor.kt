package com.fphoenixcorneae.compiler

import com.fphoenixcorneae.annotation.AndroidAspectj
import com.squareup.kotlinpoet.*
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

/**
 * @desc：@AndroidAspectj 注解处理器：generate .kt source files
 * @date：2021-06-23 15:40
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class KotlinPoetProcessor : AbstractProcessor() {
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
            val pointcutAnnotation = AnnotationSpec.builder(Pointcut::class)
                .apply {
                    val annotation = element.getAnnotation(AndroidAspectj::class.java)
                    val value = annotation.value
                    if (value.isNotEmpty()) {
                        addMember("\"" + value + "\"")
                    } else {
                        addMember("\"execution(@" + classElement.qualifiedName + " * *(..))\"").build()
                    }
                }
                .build()
            val pointcutMethod = FunSpec.builder("pointcut" + classElement.simpleName)
                .returns(Unit::class)
                .addAnnotation(pointcutAnnotation)
                .build()
            val aroundAnnotation = AnnotationSpec.builder(Around::class)
                .addMember("\"" + pointcutMethod.name + "()\"")
                .build()
            val aroundMethod = FunSpec.builder("around" + classElement.simpleName)
                .returns(Unit::class)
                .addParameter("joinPoint", ProceedingJoinPoint::class)
                .addAnnotation(aroundAnnotation)
                .addStatement(
                    "if (joinPoint.signature !is %T){\n\tthrow %T(\"This annotation can only be used on Methods\")\n}",
                    MethodSignature::class.java,
                    IllegalStateException::class.java
                )
                .addStatement("val methodSignature = joinPoint.signature as MethodSignature")
                .addStatement("""val annotation = methodSignature.method.getAnnotation(${classElement.simpleName}::class.java) as? ${classElement.simpleName}""")
                .addStatement("%T.notifyHandler(" + classElement.simpleName + "::class.java, joinPoint)",
                    ClassName("com.fphoenixcorneae.aspectj", "AspectjHandler")
                )
                .build()
            val packageName = packageElement.qualifiedName.toString()
            val classSimpleName = "${classElement.simpleName}Aspectj"
            val companionObject = TypeSpec.companionObjectBuilder()
                .build()
            val aspectj = TypeSpec.classBuilder(classSimpleName)
                .addType(companionObject)
                .addFunction(pointcutMethod)
                .addFunction(aroundMethod)
                .addAnnotation(AnnotationSpec.builder(Aspect::class).build())
                .build()
            val kotlinFile =
                FileSpec.builder(packageName, classSimpleName)
                    .addType(aspectj)
                    .build()

            mFiler?.let {
                kotlinFile.writeTo(it)
            }
        }
        return true
    }
}