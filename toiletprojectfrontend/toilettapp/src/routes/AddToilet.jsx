import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { sendNewToiletToServer } from "../servercalls/Calls";


export default function AddToilet() {
    const navigate = useNavigate()
    const [toiletData, setToiletData] = useState({
        latitude: "",
        longitude: ""
    });
    const [toiletCreated, setToiletCreated] = useState(false)

    const handleChange = e => {
        setToiletData({
            ...toiletData,
            [e.target.name]: e.target.value
        })
    };

    const handleSubmit = e => {
        e.preventDefault()
       sendNewToiletToServer(toiletData)
            .then(r => {
                navigate({
                    pathname: '/',
                    state: r
                })
            })
    }

    return(
        <div>
            <h2>create toilet</h2>
            <form onSubmit={handleSubmit} >
                <label>
                    Latitude:
                    <input
                        type="text"
                        value={toiletData.latitude}
                        name="latitude"
                        placeholder=""
                        onChange={handleChange}
                    />
                </label>
            <br/>
                <label>
                    Longitude:
                    <input
                        type="text"
                        value={toiletData.longitude}
                        name="longitude"
                        placeholder=""
                        onChange={handleChange}
                    />
                </label>
                <br/>
                <button>submit</button>
            </form>
            <Link to="/">To toiletmap</Link>
        </div>
    )
}