$(document).ready(function () {
    $(".button-collapse").sideNav({
        menuWidth: 300,
        edge: 'right'
    }, $("#slide-out").removeClass('hide'));

    $('#username_label').text(JSON.parse(localStorage.user).email);

    let adminView = $('#admin_view');
    adminView.prop('checked', (localStorage.admin === 'true'));

    adminView.change((data) => {
        localStorage.admin = data.target.checked;
        console.log(localStorage.admin);
        switchAdminView();
    });
    switchAdminView();
});

function switchAdminView() {
    let admin = JSON.parse(localStorage.admin);

    if (admin){
        $(".admin").removeClass('hide');
    }else{
        $(".admin").addClass('hide');
    }
}