<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Car market</title>

    <style>
        .wrapper {
            width: 300px;
            height: 300px;
            border: 5px solid #515151;
        }
        .logo {
            overflow: hidden;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .logo img {
            height: 100%;
            width: auto;
        }
    </style>
</head>
<body>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="/job4j_cars/js/filter.js"></script>
<script type="text/javascript">
</script>

<div class="container">
        <div class="logo">
            <a href="#"><img class="logo_img" src="images/1.png" alt=""></a>
        </div>

    <div class="row">
        <ul class="nav">
            <c:if test="${user == null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Login</a>
                </li>
            </c:if>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out
                            value="${user.name}"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">| Logout</a>
                </li>
            </c:if>
        </ul>
    </div>
    <form id="form_ads">
        <div>
            <button type="button" class="btn btn-success" onclick="window.location.href =
            'http://localhost:8080/job4j_cars/ad.do';">
                Publish new ad
            </button>
        </div>
        <br/>
        <br/>
        <h5>Filters:</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="inputBrand">Brand</label>
                <select id="inputBrand" class="form-control">
                    <option selected>Choose...</option>
                </select>
            </div>
            <div class="form-group col-md-4" id="selectModels">
                <label for="inputModel">Model</label>
                <select id="inputModel" class="form-control">
                    <option selected>Choose...</option>
                </select>
            </div>
            <div class="form-group col-md-4">
                <label for="inputCarBody">Car body</label>
                <select id="inputCarBody" class="form-control">
                    <option selected>Choose...</option>
                </select>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4">
                <input type="checkbox" id="showOnlyWithPhoto" hidden>
                <label class="form-check-label" for="showOnlyWithPhoto" hidden>Show ads with photo</label>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4">
            <button type="button" class="btn btn-success" onclick="loadAds()">Apply filters</button>
                </div>
        </div>
    </form>
    <br/>
    <div>
        <h5>Cars for sale:</h5>
        <table width="80%" class="table table-dark" id="ads_tab" name="ads">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">Ad</th>
                <th scope="col">Description</th>
                <th scope="col">Brand</th>
                <th scope="col">Model</th>
                <th scope="col">Car body</th>
            </tr>
            </thead>
            <tbody id="table_body">
            </tbody>
        </table>
    </div>
</div>
</body>
</html>