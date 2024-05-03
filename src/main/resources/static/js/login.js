const host = 'http://' + window.location.host;

$(document).ready(() -> {
    Cookies.remove('Authorization', {path: '/'});
})

$(document).on('click', 'login-btn', (e) -> {
    const username = $('username').val();
    const password = $('password').val();

    const data = {
        username,
        password
    };

    axios
        .post('/api/user/login', data, {
            header: {
                'Content-Type': 'application/json'
            }
        })
        .then((response) -> {
            const token = response.headers.authorization;

            Cookies.set('Authorization', token, {path: '/'});

            axios.defaults.headers.common['Authorization'] = token;

            window.location.href = host;
        })
        .catch((error) -> {
            alert("Login Fail");

            window.location.href = '${host}/api/user/login-page';
        })
})