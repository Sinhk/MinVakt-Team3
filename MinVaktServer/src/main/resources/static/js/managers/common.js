$(document).ready(function () {
    $(".button-collapse").sideNav({
        menuWidth: 300,
        edge: 'right'
    }, $("#slide-out").removeClass('hide'));

    $('#username_label').text(JSON.parse(sessionStorage.user).email);

    let adminView = $('#admin_view');
    adminView.prop('checked', (sessionStorage.admin === 'true'));

    adminView.change((data) => {
        sessionStorage.admin = data.target.checked;
        switchAdminView();
    });
    switchAdminView();
});



function switchAdminView() {
    var event = new Event('viewChange');
    let admin = JSON.parse(sessionStorage.admin);

    if (admin){
        $(".admin").removeClass('hide');
    }else{
        $(".admin").addClass('hide');
    }
    document.dispatchEvent(event);
}