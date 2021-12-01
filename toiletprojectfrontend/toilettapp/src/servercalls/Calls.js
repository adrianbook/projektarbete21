
 const sendNewUserToServer = (userData) => {
    return fetch('http://localhost:8080/api/user/save',
        {
            method: 'POST',
            body: JSON.stringify(userData),
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            mode: 'cors'
        })
        .then(res => res.status === 201
        );
    }

 const loginCall = (credentials) => {
     return fetch('http://localhost:8080/login', {
         method: 'POST',
         body: new URLSearchParams({
             'username': credentials.username,
             'password': credentials.password
         }),
         headers: {
             // 'Content-Type': 'application/json'
             'Content-Type': 'application/x-www-form-urlencoded',
         }
     })
         .then(res => res.json())
         .then(res => res.acces_token
         );
 }

export {sendNewUserToServer, loginCall}