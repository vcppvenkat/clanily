 
<%@ taglib uri="jakarta.tags.core" prefix="c"%>



<aside id="menu">
	<div id="navigation">



		<ul class="nav" id="side-menu">
		
			<c:choose>
				<c:when test="${thisPage.equals('home')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<a href="/home/"> <span class="nav-label">Home</span>
			</a></li>
			
			<c:choose>
				<c:when test="${thisPage.equals('transaction')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<a href="/transactions/"> <span class="nav-label">Transactions</span>
			</a></li>
			
			<c:choose>
				<c:when test="${thisPage.equals('accounts')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			
			<a href="/accounts/"> <span class="nav-label">Accounts</span>
			</a></li>

<!--  
			
			<c:choose>
				<c:when test="${thisPage.equals('projects')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<a href="projects.html"> <span class="nav-label">Projects</span>
			</a></li>
-->
			<c:choose>
				<c:when test="${thisPage.equals('loan')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<a href="/loans/"> <span class="nav-label">Loan</span>
			</a></li>
			
			<c:choose>
				<c:when test="${thisPage.equals('budget')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<a href="/budget/"> <span class="nav-label">Budget</span>
			</a></li>
			
			<c:choose>
				<c:when test="${thisPage.equals('goals')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<a href="goals.html"> <span class="nav-label">Goals</span>
			</a></li>
			
			<c:choose>
				<c:when test="${thisPage.equals('reports')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<a href="reports.html"> <span class="nav-label">Reports</span>
			</a></li>
			
			<c:choose>
				<c:when test="${thisPage.equals('notifications')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<a href="notifications.html"> <span class="nav-label">Notifications</span> <span class="badge badge-danger pull-right">10</span>
			</a></li>
			
			<c:choose>
				<c:when test="${thisPage.equals('assets')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<li><a href="assets.html"> <span class="nav-label">Assets</span>
			</a></li>

			<c:choose>
				<c:when test="${thisPage.equals('settings')}">
					<li class="active">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>


			<a href="/settings/"> <span class="nav-label">Settings</span>
			</a></li>
		</ul>
	</div>
</aside>