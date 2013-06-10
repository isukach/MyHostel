if (typeof window.getComputedStyle !== 'function') {
    window.getComputedStyle = function(el, pseudo) {
        this.el = el;
        this.getPropertyValue = function(prop) {
            var re = /(\-([a-z]){1})/g;
            if (prop == 'float') prop = 'styleFloat';
            if (re.test(prop)) {
                prop = prop.replace(re, function () {
                    return arguments[2].toUpperCase();
                });
            }
            return el.currentStyle[prop] ? el.currentStyle[prop] : null;
        };
        return this;
    }
}

if (typeof window.addEventListener === 'function') {
    window.addEventListener('load', function () {
        addDialogColor();
        addSearchBarColor();
        addTooltipColor();
        disableNewsCategoriesSelection();
    });
}

function disableNewsCategoriesSelection () {
    var labels = document.querySelectorAll('.news-categories label');
    for (var label in labels) {
        if (labels.hasOwnProperty(label)) {
            labels[label].addEventListener('mousedown', function (event) {
                event.preventDefault();
            });
        }
    }
}

function getPageColor () {
    if (document.querySelector('.header_bar')) {
        var headerBar = document.querySelector('.header_bar');
        return getComputedStyle(headerBar).getPropertyValue("background-color");
    }
    else {
        return "#616161";
    }
}

function addDialogColor () {
    var color = getPageColor();
    document.styleSheets[0].insertRule("#dialog-container, #navigation_dialog { " +
        "border-top: 10px solid " + color + ";" +
        "}", 0);
    document.styleSheets[0].insertRule(".active_stage { " +
        "border-bottom-color: " + color + "!important;" +
        "}", 1);
}

function addSearchBarColor () {
    var color = getPageColor();
    document.styleSheets[0].insertRule("#search-results { " +
        "background-color: " + color + ";" +
        "}", 2);
    document.styleSheets[0].insertRule("#search-results table td { " +
        "border-bottom-color: " + color + " !important;" +
        "}", 3);
}

function addTooltipColor () {
    var color = getPageColor();
    document.styleSheets[0].insertRule("span.icon .tooltip-container .tooltip { " +
        "background-color: " + color + " !important;" +
        "}", 4);
    document.styleSheets[0].insertRule("span.icon .tooltip-container .tooltip::before { " +
        "border-bottom-color: " + color + " !important;" +
        "}", 5);
}



