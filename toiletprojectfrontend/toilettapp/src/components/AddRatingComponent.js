import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { addRating } from "../servercalls/Calls";

const AddRatingComponent = () => {
    const navigate = useNavigate();
    const [ratingData, setRatingData] = useState({
        toiletId: "",
        rating: "",
        notes: ""
    })

    const handleChange = e =>  {
        setRatingData({
            ...ratingData,
            [e.target.name]: e.target.value
        })
    }
    const handleSubmit = e => {
        e.preventDefault()
        ratingData.toiletId = sessionStorage.getItem("toiletToRate")

        addRating(ratingData)
            .then(addedRating => {
                window.location.reload(false)
                navigate({
                    pathname: '/',
                    state: addedRating
                })
            })
            .catch(error => {
                sessionStorage.setItem("loggedInUser", "")
                window.location.reload(false)
                console.log(error)
                prompt(error.message)
            })
    }

    return(
        <div>
            <h2>Add rating to toilet</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Rating
                    <input
                        type="number"
                        min="1"
                        max="5"
                        value={ratingData.rating}
                        name="rating"
                        placeholder=""
                        onChange={handleChange}
                    />
                </label>
                <br/>
                <label>
                    Notes
                    <input
                        type="text"
                        value={ratingData.notes}
                        name="notes"
                        placeholder=""
                        onChange={handleChange}
                    />
                </label>
                <br/>
                <button >submit</button>
            </form>
        </div>
    )
}
export default AddRatingComponent