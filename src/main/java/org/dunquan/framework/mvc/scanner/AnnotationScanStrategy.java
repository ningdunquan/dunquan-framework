package org.dunquan.framework.mvc.scanner;

import java.lang.annotation.Annotation;

public class AnnotationScanStrategy extends ScanStrategy {
	private Class<? extends Annotation> clazz;

	public AnnotationScanStrategy(String packageName, Class<? extends Annotation> clazz) {
		super(packageName);
		this.clazz = clazz;
	}

	@Override
	public boolean checkAddClass(Class<?> cls) {
		return cls.isAnnotationPresent(clazz);
	}

}
