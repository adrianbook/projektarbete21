/* 
Refaktorerat lite här och lagt till en privat metod för att göra markers av toaletter 
*/

const getAllToiletsCall = () => {
    return fetch("http://localhost:9091/api/v1/toilets/getalltoilets",
        {method: "GET"})
        .then(res => {
            return  res.json()
        })
        .then(obj => obj.toilets.map(toilet => turnToiletIntoMarker(toilet)))
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
        body: JSON.stringify({latitude: parseFloat(toiletData.latitude),
                                    longitude: parseFloat(toiletData.longitude),
                                    urinal: toiletData.urinal,
                                    separateGenders: toiletData.separateGenders,
                                    changingTable: toiletData.changingTable,
                                    shower: toiletData.shower,
                                    handicapFriendly: toiletData.handicapFriendly
        }),
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
        .then(toilet => turnToiletIntoMarker(toilet))

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
        if (r.status === 500) throw new Error(r.json)
        if (r.status !== 201 && r.status !== 200) throw new Error("unexpected error occured adding rating")
        return r.json()
    })
    .then(ratingObj => turnToiletIntoMarker(ratingObj.toilet))
}


const getAllUsers = () => {
    return fetch("http://localhost:8080/api/users", {
        method: "GET",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        }
    })
        .then(res =>{
            if (res.status === 403) throw new Error("You can not fetch users")
            if (res.status === 500) throw new Error("Error fetching users")
            if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
            return res.json()
        })
}
const blockUser = (username) => {
    return fetch("http://localhost:8080/api/user/block", {
        method: "PUT",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username
        })
    })
        .then(res => {
            if (res.status === 403) throw new Error("You can not fetch users")
            if (res.status === 404) throw new Error("User not found")
            if (res.status === 500) throw new Error("Error fetching users")
            if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
            return res.json()
        })
}
const unBlockUser = (username) => {
    return fetch("http://localhost:8080/api/user/unblock", {
        method: "PUT",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username
        })
    })
        .then(res => {
            if (res.status === 403) throw new Error("You can not fetch users")
            if (res.status === 404) throw new Error("User not found")
            if (res.status === 500) throw new Error("Error fetching users")
            if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
            return res.json()
        })
}
const fetchUserByUsername = (username) => {
    return fetch("http://localhost:8080/api/user/" + username, {
        method: "GET",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        },
    } )
        .then(res => {
            if (res.status === 403) throw new Error("You can not fetch users")
            if (res.status === 404) throw new Error("User not found")
            if (res.status === 500) throw new Error("Error fetching users")
            if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
            return res.json()
        })

}
const addRole = (data) => {
    return fetch("http://localhost:8080/api/role/addtouser", {
        method: "POST",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: data.username,
            rolename: data.rolename
        })
    })
        .then(res => {
            if (res.status === 403) throw new Error("Action not allowed")
            if (res.status === 404) throw new Error("Not Found")
            if (res.status === 500) throw new Error("Serverside error")
            if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
            return res.json()
        })
}
const getAllReports = () => {
     return fetch("http://localhost:9091/admin/api/v1/toilets/reports/getall", {
        method: "GET",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        }
    })
         .then(res => {
        if (res.status === 403) throw new Error("Action not allowed")
        if (res.status === 404) throw new Error("Not Found")
        if (res.status === 500) throw new Error("Serverside error")
        if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
        return res.json()
    })
}

const getAllUserdefinedReports = () => {
    return fetch("http://localhost:9091/admin/api/v1/toilets/reports/getalluserdefinedreports", {
        method: "GET",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            if (res.status === 403) throw new Error("Action not allowed")
            if (res.status === 404) throw new Error("Not Found")
            if (res.status === 500) throw new Error("Serverside error")
            if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
            return res.json()
        })
}

const getAllNonExistingToiletsReports = () => {
    return fetch("http://localhost:9091/admin/api/v1/toilets/reports/getallnonexistingtoilets", {
        method: "GET",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            if (res.status === 403) throw new Error("Action not allowed")
            if (res.status === 404) throw new Error("Not Found")
            if (res.status === 500) throw new Error("Serverside error")
            if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
            return res.json()
        })
}

const sendReportToServer = reportData => {
    return fetch("http://localhost:9091/api/v1/toilets/reports/report", {
        method: "POST",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reportData)
    })
    .then(response => {
        if (response.status === 500) throw new Error(response.json())
        if (response.status !== 201) throw new Error("Unknown problem occured connecting to server")
        return response.json()
    })
    .then(respObj => {
        respObj.toilet = turnToiletIntoMarker(respObj.toilet)
        return respObj
    })
}

const deleteToilet = toiletID => {
    return fetch("http://localhost:9091/admin/api/v1/toilets/" + toiletID, {
        method: "DELETE",
        headers: {
            'AUTHORIZATION': sessionStorage.getItem('loggedInUser'),
            'Content-Type': 'application/json'
        }
    }).then(res => {
            if (res.status === 403) throw new Error("Action not allowed")
            if (res.status === 404) throw new Error("Not Found")
            if (res.status === 500) throw new Error("Serverside error")
            if (res.status !== 200) throw new Error("Unexpected error occurred fetching users")
            return res.status
        }
    )
}

const turnToiletIntoMarker = (toilet) => {
    return {thispos: [toilet.latitude, toilet.longitude],
            id: toilet.id,
            avgRat: toilet.avgRating,
            urinal: toilet.urinal,
            separateGenders: toilet.separateGenders,
            changingTable: toilet.changingTable,
            shower: toilet.shower,
            handicapFriendly: toilet.handicapFriendly}
}


export {sendNewUserToServer, loginCall, sendNewToiletToServer, verifyUser , getAllToiletsCall, addRating, getAllUsers, blockUser,unBlockUser, fetchUserByUsername, addRole, sendReportToServer ,getAllReports, getAllUserdefinedReports, getAllNonExistingToiletsReports, deleteToilet}

