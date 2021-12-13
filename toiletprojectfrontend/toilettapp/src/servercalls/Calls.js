
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
const sendNewToiletToServer = (toiletData) => {
    return fetch('http://localhost:9091/api/v1/toilets/create', {
        method: 'POST',
        body: JSON.stringify({latitude: parseFloat(toiletData.latitude), longitude: parseFloat(toiletData.longitude)}),
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        }
    } ).then(r => {
        if (r.status !== 201) throw new Error()
        console.log(r.status)
        return r.json()
    })

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

export {sendNewUserToServer, loginCall, sendNewToiletToServer}