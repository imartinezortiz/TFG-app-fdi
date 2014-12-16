<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<script type="text/javascript">
	$('#myModal').modal({
		show : false,
		keyboard : false
	});
</script>

<section class="container">
	<h3>
		<a href="<spring:url value="/avisos/gestor/crear"></spring:url>">
			Crear nuevo aviso</a>
	</h3>
</section>


<section class="container">
	<!-- <div class="row"> -->
	<c:forEach items="${avisos}" var="aviso">
		<div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
			<!-- <div class="row "> -->
			<%-- <img
							src="<c:url value="/resource/images/${aviso.avisoId}.png"></c:url>"
							alt="image" style="width: 100%" /> --%>
			<div class="caption panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<!-- Ver aviso -->
						<a
							href="<c:url value="/avisos/ver/individual?id=${aviso.postInternalId}"></c:url>">${aviso.titulo}</a>
						<!-- Editar aviso -->
						<a class="pull-right"
							href="<c:url value="/avisos/gestor/editar?id=${aviso.postInternalId}"></c:url>">
							<span hint="Editar aviso" class="glyphicon glyphicon-edit "></span>
						</a>


						<%-- <!-- Eliminar aviso -->
							<a class="pull-right"
								href="<c:url value="/avisos/gestor/eliminar?id=${aviso.postInternalId}"></c:url>">
								<span class="glyphicon glyphicon-remove-sign"></span>
							</a> --%>

						<!-- Bot�n eliminar -->
						<a data-toggle="modal" class="pull-right btn"
							data-target=".bs-delete-modal-sm${aviso.postInternalId}"> <span
							class="glyphicon glyphicon-remove"></span>
						</a>

						<!-- Popup de confirmaci�n de eliminaci�n -->
						<div class="modal fade bs-delete-modal-sm${aviso.postInternalId}"
							tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"
							aria-hidden="true">
							<div class="modal-dialog modal-sm">
								<div class="modal-content">
									<div class="modal-header bg-primary">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="myModalLabel">Eliminar aviso</h4>
									</div>
									<div class="modal-body">�Est� seguro de que desea
										eliminar el aviso "${aviso.titulo}"?</div>
									<div class="modal-footer ">
										<a class="btn btn-danger "
											href="<c:url value="/avisos/gestor/eliminar?id=${aviso.postInternalId}"></c:url>">
											Eliminar </a>
										<button type="button" class="btn btn-warning "
											data-dismiss="modal">Cancelar</button>
									</div>
								</div>
							</div>
						</div>
						<!-- Fin de  Popup de confirmaci�n de eliminaci�n -->


					</div>
				</div>
				<h3></h3>
				<div class="panel-body">
					<p>${aviso.fechaPublicacion}</p>
					<p>${aviso.tipoAviso}</p>
				</div>
				<div class="panel-footer">
					<p>
						<span class="label label-warning">${aviso.etiqueta}</span>
					</p>
				</div>
			</div>
			<!-- </div> -->
		</div>
	</c:forEach>
	<!-- </div> -->
</section>