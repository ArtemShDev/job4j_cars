document.addEventListener('DOMContentLoaded', loadFilters);

function validate() {
    if ($('#name').val() === '') {
        alert($('#name').attr('placeholder'));
        return false;
    }
    if ($('#description').val() === '') {
        alert($('#description').attr('placeholder'));
        return false;
    }
    if ($('#inputBrand').val() === 'Choose...') {
        alert('Please, choose a Brand');
        return false;
    }
    if ($('#inputModel').val() === 'Choose...') {
        alert('Please, choose a Model');
        return false;
    }
    if ($('#inputCarBody').val() === 'Choose...') {
        alert('Please, choose a Car body');
        return false;
    }
    return true;
}

$('#photo').on('change', function () {
    console.log($('#photo')[0].files);//contains base64 encoded string array holding the
});

function updateImageDisplay() {
    var input = document.getElementById('photo');
    var preview = document.getElementById('preview');
    var curFiles = input.files;
    for (var i = 0; i < curFiles.length; i++) {
        var listItem = document.createElement('li');
        var image = document.createElement('img');
        image.src = window.URL.createObjectURL(curFiles[i]);
        image.width = 110;
        image.height = 110;
        listItem.appendChild(image);
        console.log(curFiles[i].name);
        console.log(curFiles[i]);
        preview.appendChild(listItem);
    }
}

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
    }).fail(function (err) {
        console.log(err);
    });
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

function selectBrand(name = "") {
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
        }
        ;
    }).fail(function (err) {
        console.log(err);
    });
}

function clearModels() {
    var elem = document.getElementById('inputModel');
    elem.parentNode.removeChild(elem);
    var select = document.createElement('select');
    select.setAttribute("id", "inputModel");
    select.setAttribute("class", "form-control");
    select.setAttribute("name", "model");
    let option = document.createElement('option');
    option.innerHTML = "Choose...";
    option.setAttribute("selected", "selected");
    select.appendChild(option);
    var elemTab = document.getElementById('selectModels');
    elemTab.appendChild(select);
    return false;
}

function addRowToListModels(model) {
    var select = document.getElementById('inputModel');
    var val = select.getAttribute("value");
    if (val === model.name) {
        return;
    }
    let option = document.createElement('option');
    option.setAttribute("idDB", model.id);
    option.value = model.id;
    option.innerHTML = model.name;
    select.appendChild(option);
}

function addRowToList(brand) {
    var select = document.getElementById('inputBrand');
    var val = select.getAttribute("value");
    if (val === brand.name) {
        return;
    }
    let option = document.createElement('option');
    option.value = brand.id;
    option.setAttribute("idDB", brand.id);
    option.innerHTML = brand.name;
    select.appendChild(option);
}

function addRowToListCB(cb) {
    var select = document.getElementById('inputCarBody');
    var val = select.getAttribute("value");
    if (val === cb.name) {
        return;
    }
    let option = document.createElement('option');
    option.value = cb.id;
    option.setAttribute("idDB", cb.id);
    option.innerHTML = cb.name;
    select.appendChild(option);
}