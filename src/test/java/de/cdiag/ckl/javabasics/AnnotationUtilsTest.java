package de.cdiag.ckl.javabasics;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Ignore
public class AnnotationUtilsTest {

	public interface A<T> {
		@Transactional
		T method( T t );
	}

	public static class B implements A<String> {
		@Override
		public String method( String s ) {
			return s;
		}
	}

	@Test
	public void interfaceAnnotation()
		throws NoSuchMethodException {
		Method method = B.class.getMethod( "method", String.class );
		Assert.assertNotNull( "method not found", method );
		Transactional annotation = AnnotationUtils.findAnnotation( method, Transactional.class );
		Assert.assertNotNull( "annotation not found", annotation );
	}

	@Test
	public void interfaceAnnotation2()
		throws NoSuchMethodException {
		Method method = B.class.getMethod( "method", Object.class );
		Assert.assertNotNull( "method not found", method );
		Transactional annotation = AnnotationUtils.findAnnotation( method, Transactional.class );
		Assert.assertNotNull( "annotation not found", annotation );
	}

}
