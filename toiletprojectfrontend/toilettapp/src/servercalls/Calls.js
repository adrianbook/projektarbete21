 const getAllToiletsCall = () => {
    return fetch("http://localhost:9091/api/v1/toilets/getalltoilets",
        {method: "GET"})
            .then(res => {
            return  res.json()
        })
        .then(obj => {
        const positions = []
        obj.toilets.forEach(pos => {
            positions.push([pos.longitude, pos.latitude])
        });
        return positions
    })
    .catch(error => {
        console.log("Error: "+error)
    })
 }
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
     console.log("inside loginCall")
     console.log(credentials)
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


const verifyUser = (token) => {
    console.log("INSIDE VERIFYUSER")
    return fetch('http://localhost:8080/api/verifyuser',{
        method: "GET",
        headers: {
            'AUTHORIZATION': token,
            'Content-Type': 'application/json'
        }
    }).then(res => res.status === 200)
}

export {sendNewUserToServer, loginCall, sendNewToiletToServer, verifyUser , getAllToiletsCall}

