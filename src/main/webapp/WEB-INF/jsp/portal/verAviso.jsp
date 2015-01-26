<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<script src="/webstore/resource/js/controllers.js"></script>

	<div class="jumbotron">
		<div class="container">
			<h1>"${aviso.titulo}"</h1>
			<b>PublicaciÃ³n</b>${fn:substring(aviso.fechaPublicacionInicio,0,19	)}
		</div>
	</div>

<section class="container col-md-offset-2 col-md-6 center">
	<div class="panel panel-info">
		<%-- <h3>${aviso.titulo}</h3> --%>
		<div class="panel-heading">

			<div class="panel-title">
				${aviso.titulo} <a class="pull-right"
					href="<c:url value="/avisos/gestor/editar?id=${aviso.postInternalId}"></c:url>">
					<span hint="Editar aviso" class="glyphicon glyphicon-edit "></span>
				</a>

			</div>

		</div>
		<div class="panel-body clearfix" style="padding-top: 7.5px;">
			<p>${aviso.contenidoAviso}</p>
			<p><strong>Lugar : </strong> ${aviso.lugar}</p>
			<p><strong>DuraciÃ³n estimada : </strong>${aviso.duracionEstimada}</p>
			<p><strong>Aforo : </strong>${aviso.numeroPlazas}</p>
			<p><strong>Creado por: </strong>${aviso.autor}</p>
			<p>
				<strong>Etiqueta : </strong> <span class=" label label-warning">${aviso.etiqueta}</span>
			</p>
			<p>
				<strong>Fecha evento</strong> : ${aviso.fechaEvento}
			</p>
			<p>
				<strong>Tipo aviso</strong> : ${aviso.tipoAviso}
			</p>
			<p>
				<strong>Fecha creaciÃ³n:</strong>
				${fn:substring(aviso.fechaCreacion,0,19	)}
			</p>
			<p>
				<strong>Fecha publicaciÃ³n (desde):</strong>
				${fn:substring(aviso.fechaPublicacionInicio,0,19	)}
			</p>
			<p>
				<strong>Fecha publicaciÃ³n (hasta):</strong>
				${fn:substring(aviso.fechaPublicacionFin,0,19	)}
			</p>
			<p>
				<strong>NÃºmero de visitas:</strong>
				${aviso.numeroVisitas}
			</p>
		</div>


	</div>

</section>