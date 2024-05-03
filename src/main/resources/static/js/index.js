const host = 'http://' + window.location.host;

$(document).ready(function () {
    const auth = getToken();

        if (auth !== undefined && auth !== '') {
            $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
                jqXHR.setRequestHeader('Authorization', auth);
            });
        } else {
            window.location.href = host + '/api/user/login-page';
            return;
        }
})

function getToken() {
    let auth = Cookies.get('Authorization');

    if(auth === undefined) {
        return '';
    }

    return auth;
}