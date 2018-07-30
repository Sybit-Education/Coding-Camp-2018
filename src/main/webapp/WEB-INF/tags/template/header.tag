<%@ tag body-content="empty" trimDirectiveWhitespaces="true"  pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="title" required="true" type="String" %>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<c:url value="/" />" />

<link rel="icon" href="<c:url value="resources/favicon.ico" />" />

<!-- Material Design for Bootstrap fonts and icons -->
<link rel="stylesheet" href="<c:url value="resources/external-libs/styles/material-design-icons.css" />">
<link rel="stylesheet" href="<c:url value="resources/external-libs/styles/bootstrap-material-design.min.css" />">
<link rel="stylesheet" href="<c:url value="resources/styles/style.css" />">

<title>${title}</title>
