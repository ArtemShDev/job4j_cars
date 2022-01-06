<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <title>Car market</title>
    <style>

        ul#preview {
            padding-inline-start: 0px;
        }

        ul#preview li {
            display: inline;
            margin-right: 5px;
        }

        ul#photoDB {
            padding-inline-start: 0px;
        }

        ul#photoDB li {
            display: inline;
            margin-right: 5px;
        }
    </style>
</head>

<body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="/job4j_cars/js/ad4.js"></script>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out
                            value="${user.name}"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Exit</a>
                </li>
            </c:if>
        </ul>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <c:if test="${ad != null}">
                    Ad.
                </c:if>
                <c:if test="${ad == null}">
                    Publishing a new ad.
                </c:if>
            </div>
            <div class="card-body">
                <form action="<c:url value='/ad.do?id=${ad.id}'/>" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" name="name" id="name"
                               placeholder="Briefly describe the ad"
                               value="<c:out value="${ad.name}"/>">
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <input type="text" class="form-control" name="description" id="description"
                               placeholder="Describe the details of ad"
                               value="<c:out value="${ad.description}"/>">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="inputBrand">Brand</label>
                            <select id="inputBrand" name="brand" class="form-control"
                                    value="<c:out value="${ad.brand.name}"/>">
                                <option selected>Choose...</option>
                                <c:if test="${ad != null}">
                                    <option idDB="<c:out value="${ad.brand.id}"/>"
                                            value="<c:out value="${ad.brand.id}"/>"
                                            selected><c:out value="${ad.brand.name}"/></option>
                                </c:if>
                            </select>
                        </div>
                        <div class="form-group col-md-4" id="selectModels">
                            <label for="inputModel">Model</label>
                            <select id="inputModel" name="model" class="form-control"
                                    value="<c:out value="${ad.model.name}"/>">
                                <option selected>Choose...</option>
                                <c:if test="${ad != null}">
                                    <option idDB="<c:out value="${ad.model.id}"/>"
                                            value="<c:out value="${ad.model.id}"/>"
                                            selected><c:out value="${ad.model.name}"/></option>
                                </c:if>
                            </select>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputCarBody">Car body</label>
                            <select id="inputCarBody" name="carBody" class="form-control"
                                    value="<c:out value="${ad.carBody.name}"/>">
                                <option selected>Choose...</option>
                                <c:if test="${ad != null}">
                                    <option idDB="<c:out value="${ad.carBody.id}"/>"
                                            value="<c:out value="${ad.carBody.id}"/>"
                                            selected><c:out value="${ad.carBody.name}"/></option>
                                </c:if>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <input type="checkbox" id="sold" name="sold" value="true"
                               <c:if test="${ad.sold == true}">checked</c:if>>
                        <label class="form-check-label" for="sold">Sold</label>
                    </div>
                    <br/>
                    <div>
                        <ul id="photoDB">
                            <c:forEach items="${ad.photo}" var="ph">
                                <li>
                                    <img class="db_img" width="150px" height="150px" src="<c:out value="${ph.path}"/>"
                                         alt="">
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div>
                        <ul id="preview">

                        </ul>
                    </div>
                    <div>
                        <h6>Upload photo</h6>
                        <div class="checkbox">
                            <input type="file" id="photo" name="photo" multiple accept="image/*,image/jpeg,image/png">
                        </div>
                    </div>
                    <br/>
                    <button type="submit" class="btn btn-primary" onclick="return validate();">Publish</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    <c:if test="${user == null || (ad.user.id != user.id) && ad != null}">
    var inputs = document.getElementsByTagName("input");
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].disabled = true;
    }
    var selects = document.getElementsByTagName("select");
    for (var i = 0; i < selects.length; i++) {
        selects[i].disabled = true;
    }
    var buttons = document.getElementsByTagName("button");
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].disabled = true;
    }
    </c:if>
</script>
<script>
    document.getElementById('photo').addEventListener('change', updateImageDisplay);
</script>
</html>