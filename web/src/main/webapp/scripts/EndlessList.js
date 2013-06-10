/**
 * User: a.garelik
 * Date: 22/01/13
 * Time: 17:06
 */

var isLoading = false;
var targetUrl;
var elementID;
var oldStyle = "";
var scrollPosition = 0

function onAJAXLoad(){
    var scrollable = document.getElementById(elementID);
    isLoading = false;
    oldStyle = scrollable.getAttribute("style");
    scrollPosition = scrollable.scrollTop;
    callWicket()
}
function callWicket() {
    var wcall = wicketAjaxGet(targetUrl,
        function(){
            var scrollable = document.getElementById(elementID);
            scrollable.setAttribute('style' ,oldStyle);
            scrollable.scrollTop = scrollPosition;
            isLoading = false
        }
    );
}
function scroll(url, id){
    targetUrl = url;
    elementID = id;
    if(isLoading) return false;
    var scrollable = document.getElementById(elementID);
    var endPos = scrollable.scrollHeight - scrollable.clientHeight - scrollable.scrollTop;
    if(endPos < 20){
        isLoading = true;
        setTimeout(onAJAXLoad, 0);
    }
}
function calculateScroll(){
    var scrollable = document.getElementById(elementID);
    scrollable.scrollTop = scrollPosition;
}