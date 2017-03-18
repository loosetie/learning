package de.cdiag.ckl.javabasics.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Christian on 18.03.2017.
 */
@Aspect
@Component
@Scope(value = "session")
@Slf4j
@RequiredArgsConstructor
public class TransactionPermissionAspect {

	private final SecurityContextHolderAwareRequestWrapper security;

	@Pointcut("within(@de.cdiag.ckl.javabasics.security.TransactionPermission *)")
	public void txPermissionsDefined() {}

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {}

	@Before("txPermissionsDefined() && publicMethod()")
	public void checkTransactionPermissions( JoinPoint jp )
		throws Throwable {
		TransactionPermission txPermission = AnnotationUtils.findAnnotation( jp.getThis().getClass(), TransactionPermission.class );
		if( txPermission != null ) {
			Method method = ((MethodSignature) jp.getSignature()).getMethod();
			TxState txState = txState( method );
			if( txState != TxState.NONE ) {
				List<String> permittedRoles = permittedRoles( txPermission, txState );
				if( !permittedRoles.isEmpty() && permittedRoles.stream().noneMatch( security::isUserInRole ) ) {
					log.info( "User '{}' tried to {} '{}', but does not have the required roles '{}'",
						security.getRemoteUser(), txState, method, permittedRoles
					);
					throw new AccessDeniedException( "Forbidden" );
				}
			}
		}
	}

	private List<String> permittedRoles( TransactionPermission txPermission, TxState txState ) {
		List<String> roles = Collections.emptyList();
		if( txState == TxState.READ ) {
			roles = Arrays.asList( txPermission.read() );
		}
		if( roles.isEmpty() || txState == TxState.WRITE ) {
			roles = Arrays.asList( txPermission.write() );
		}
		return roles;
	}

	private TxState txState( Method method ) {
		if( TransactionSynchronizationManager.isActualTransactionActive() ) {
			return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? TxState.READ : TxState.WRITE;
		}
		Transactional transactional = findAnnotation( method );
		if( transactional != null ) {
			return transactional.readOnly() ? TxState.READ : TxState.WRITE;
		}
		return TxState.NONE;
	}

	private Transactional findAnnotation( Method method ) {
		// FIXME There is a bug in Spring for generic super methods
		Transactional annotation = AnnotationUtils.findAnnotation( method, Transactional.class );
		if( annotation == null ) {
			log.debug( "No Transactional annotation found for {}", method );
		}
		return annotation;
	}

	private enum TxState {
		NONE, READ, WRITE
	}
}
