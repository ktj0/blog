const host = 'http://' + window.location.host;

$(document).ready(function () {
    Cookies.remove('Authorization', {path: '/'});
})

$(document).on('click', '#login-btn', function () {
    const username = $('#username').val();
    const password = $('#password').val();

    const data = {
        username,
        password
    };

    axios
        .post('/api/user/login', data, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(function (response) {
            const token = response.headers.authorization;

            Cookies.set('Authorization', token, {path: '/'});

            axios.defaults.headers.common['Authorization'] = token;

            window.location.href = host;
        })
        .catch(function (response) {
            alert("Login Fail");

            window.location.href = '${host}/api/user/login-page';
        })
})

$(document).on('click', '#signup-btn', function() {
    window.location.href = '/api/user/signup';
});