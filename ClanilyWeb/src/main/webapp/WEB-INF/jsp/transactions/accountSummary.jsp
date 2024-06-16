
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<div class="hpanel">

	<div class="panel-heading" style="margin: 6pt; padding-top: 0%; margin-top: 0%;">

		<ul class="mailbox-list">
			<c:forEach items="${units}" var="unit">

				<c:if test="${unit.group eq true}">
					<c:if test="${unit.selected eq true}">
						<li class="bg-primary-2"><a href="${unit.controllerMethod}" class="text-white">
					</c:if>
					<c:if test="${unit.selected eq false}">
						<li class="bg-blue-light"><a href="${unit.controllerMethod}" class="">
					</c:if>
			
										
											${unit.accountName }
											<span class="pull-right"> ${unit.balance} </span>
					</a>
					</li>
				</c:if>
				<c:if test="${unit.account eq true}">
					<c:if test="${unit.selected eq true}">
						<li class="bg-primary-2"><a href="${unit.controllerMethod}" class="text-white m-l-sm">
					</c:if>
					<c:if test="${unit.selected eq false}">
						<li><a href="${unit.controllerMethod}" class="text-muted m-l-sm">
					</c:if>
			
										
											${unit.accountName }
											<span class="pull-right"> ${unit.balance} </span>
					</a>
					</li>
				</c:if>

			</c:forEach>
		</ul>







	</div>

</div>