$(document).on('click', '#admin-check', function() {
    const isAdmin = $('#admin-check').prop('checked');
    const adminToken = $('#admin-token');

    if (isAdmin) {
        adminToken.css('display', 'block');
    } else {
        adminToken.css('display', 'none');
    }
})