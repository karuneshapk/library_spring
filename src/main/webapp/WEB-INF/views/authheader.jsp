<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<sec:authorize access="!hasRole('ADMIN') and !hasRole('MEMBER')">
		<div class="authbar">
			<span>Dear <strong>${loggedinuser}</strong>, Welcome to Library.</span> <span class="floatRight"><a href="<c:url value="/logout" />">Come or registered</a></span>
		</div>
	</sec:authorize>

	<sec:authorize access="hasRole('ADMIN') or hasRole('MEMBER')">
		<div class="authbar">
			<span>Dear <strong>${loggedinuser}</strong>, Welcome to Library.</span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
		</div>
	</sec:authorize>