
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


	<div class="modal fade" id="addPayeeModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="color-line"></div>
				<div class="modal-header text-center">
					<h4 class="modal-title">Add new payee</h4>
					<small class="font-bold">You can quickly add a new payee here. If you want to update other information of this payee, you can navigate to Settings
						--> Payee and select the payee you want to update.</small>
				</div>
				<div class="modal-body">
					
						
						
							<label class="control-label">Payee name</label>
							<form:input path="payeeName" class="form-control" placeholder="Short payee name for easy search. Maximum 25 chars. Eg. Amazon"></form:input>
					


				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</a>
					<button type="submit" class="btn btn-primary2 pull-right" onclick="addNewPayeeForm();return true;">Add new payee</button>
				</div>
			</div>
		</div>
	</div>

