package org.dunquan.framework.mvc.scanner;

import java.lang.annotation.Annotation;
import java.util.List;

public class DefaultClassScanner implements ClassScanner {

	public List<Class<?>> getClassList(String packageName) {
		return null;
	}

	public List<Class<?>> getClassListByAnnotation(String packageName,
			Class<? extends Annotation> annotationClass) {
		ScanStrategy scanStrategy = new AnnotationScanStrategy(packageName, annotationClass);
		
		return scanStrategy.getClassList();
	}

}
