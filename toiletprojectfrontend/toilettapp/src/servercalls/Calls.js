const getAllToiletsCall = () => {
    return fetch("http://localhost:9091/api/v1/toilets/getalltoilets",
        {method: "GET"})
        .then(res => {
            return  res.json()
        })
        .then(obj => {
        console.log(obj)
        const positions = []
            obj.toilets.forEach(pos => {
            positions.push({thispos: [pos.latitude, pos.longitude], id: pos.id })
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
    } )
        .then(r => {
            if (r.status === 403) throw new Error("Toilet rejected. Is the account valid and assigned a role?")
            if (r.status === 400) throw new Error("Toilet already exists")
            if (r.status !== 201) throw new Error("unexpected error occured adding toilet")
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
    return fetch('http://localhost:8080/api/verifyuser',{
        method: "GET",
        headers: {
            'AUTHORIZATION': token,
            'Content-Type': 'application/json'
        }
    }).then(res => res.status === 200)

}

const addRating = (ratingData) => {
    return fetch("http://localhost:9091/api/v1/toilets/rate", {
        method: "PUT",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({toiletId: ratingData.toiletId, rating: parseInt(ratingData.rating), notes: ratingData.notes})
    })
        .then(r => {
            if (r.status === 403) throw new Error("rating rejected. Is the account valid and assigned a role?")
            if (r.status === 500) throw new Error(r.status)
            if (r.status !== 201 && r.status !== 200) throw new Error("unexpected error occured adding rating")
            return r.json()
    })
}
const getAllUsers = () => {
    return fetch("http://localhost:8080/api/users", {
        method: "GET",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        }
    })
}

export {sendNewUserToServer, loginCall, sendNewToiletToServer, verifyUser , getAllToiletsCall, addRating, getAllUsers }

