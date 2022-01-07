document.addEventListener('DOMContentLoaded', loadFilters);

function loadFilters() {
    var select = document.getElementById('inputBrand');
    select.addEventListener("change", selectBrand);
    loadCarBodies();
}

function loadCarBodies(all = "all") {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_cars/filters?carBodies=' + all,
        dataType: 'json'
    }).done(function (data) {
        for (var cb of data) {
            addRowToListCB(cb);
        };
        loadBrands();
        loadAds();
    }).fail(function (err) {
        console.log(err);
    });
    return false;
}

function loadAds() {
    var selectBrand = document.getElementById('inputBrand');
    var selectModel = document.getElementById('inputModel');
    var selectCarBody = document.getElementById('inputCarBody');
    var showOnlyWithPhoto = document.getElementById('showOnlyWithPhoto');
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_cars/list?brand=' + selectBrand.value
            + '&model=' + selectModel.value + '&carBody='+ selectCarBody.value
        + '&showOnlyWithPhoto=' + showOnlyWithPhoto.value,
        dataType: 'json'
    }).done(function (data) {
        clearTable();
        for (var ad of data) {
            addRowToListAds(ad);
        };
    }).fail(function (err) {
        console.log(err);
    });
}

function clearTable() {
    var elem = document.getElementById('table_body');
    elem.parentNode.removeChild(elem);
    var tb = document.createElement('tbody');
    tb.setAttribute("id", "table_body");
    var elemTab = document.getElementById('ads_tab');
    elemTab.appendChild(tb);
    return false;
}

function addRowToListAds(ad) {
    var tbody = document.getElementById('table_body');
    let row = document.createElement('tr');
    row.setAttribute("id", "id_" + ad.id);
    row.setAttribute("onclick",
        "window.location.href = 'http://localhost:8080/job4j_cars/modify?id=" + ad.id + "'");
    let firstPhoto = "";
    if (ad.photo[0] != null) {
        firstPhoto = ad.photo[0].path;
    }

    let row_th = document.createElement('td');
    row_th.setAttribute("class", "table-active");
    let img = document.createElement('img');
    img.setAttribute("src", firstPhoto);
    img.setAttribute("width", "100px");
    img.setAttribute("height", "70px");
    row_th.appendChild(img);
    row.appendChild(row_th);

    let row_td = document.createElement('td');
    row_td.setAttribute("class", "table-active");
    row_td.innerHTML = ad.name;
    row.appendChild(row_td);

    row_td = document.createElement('td');
    row_td.setAttribute("class", "table-active");
    row_td.innerHTML = ad.description;
    row.appendChild(row_td);

    row_td = document.createElement('td');
    row_td.setAttribute("class", "table-active");
    row_td.innerHTML = ad.brand.name;
    row.appendChild(row_td);

    row_td = document.createElement('td');
    row_td.setAttribute("class", "table-active");
    row_td.innerHTML = ad.model.name;
    row.appendChild(row_td);

    row_td = document.createElement('td');
    row_td.setAttribute("class", "table-active");
    row_td.innerHTML = ad.carBody.name;
    row.appendChild(row_td);

    tbody.appendChild(row);
}

function loadBrands(all = "all") {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_cars/filters?brands=' + all,
        dataType: 'json'
    }).done(function (data) {
        for (var brand of data) {
            addRowToList(brand);
        };
    }).fail(function (err) {
        console.log(err);
    });
}

function selectBrand(id = 0) {
    var select = document.getElementById('inputBrand');
    if (select.value === "Choose...") {
        clearModels();
        return;
    }
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_cars/filters?models=' + select.value,
        dataType: 'json'
    }).done(function (data) {
        clearModels();
        for (var model of data) {
            addRowToListModels(model);
        };
    }).fail(function (err) {
        console.log(err);
    });
}

function addRowToListModels(model) {
    var select = document.getElementById('inputModel');
    let option = document.createElement('option');
    option.value = model.id;
    option.setAttribute("idDB", model.id);
    option.innerHTML = model.name;
    select.appendChild(option);
}

function clearModels() {
    var elem = document.getElementById('inputModel');
    elem.parentNode.removeChild(elem);
    var select = document.createElement('select');
    select.setAttribute("id", "inputModel");
    select.setAttribute("class", "form-control");
    let option = document.createElement('option');
    option.innerHTML = "Choose...";
    option.setAttribute("selected", "selected");
    select.appendChild(option);
    var elemTab = document.getElementById('selectModels');
    elemTab.appendChild(select);
    return false;
}

function addRowToList(brand) {
    var select = document.getElementById('inputBrand');
    let option = document.createElement('option');
    option.value = brand.id;
    option.setAttribute("idDB", brand.id);
    option.innerHTML = brand.name;
    select.appendChild(option);
}

function addRowToListCB(cb) {
    var select = document.getElementById('inputCarBody');
    let option = document.createElement('option');
    option.value = cb.id;
    option.setAttribute("idDB", cb.id);
    option.innerHTML = cb.name;
    select.appendChild(option);
}