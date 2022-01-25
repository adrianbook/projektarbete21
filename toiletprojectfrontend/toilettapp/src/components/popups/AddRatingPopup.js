import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { addRating } from "../../servercalls/Calls";

const AddRatingPopup = (props) => {

    const [ratingData, setRatingData] = useState({
        toiletId: props.marker.id,
        rating: "",
        notes: ""
    })


    useEffect(()=>{
        setRatingData({
            ...ratingData,
            toiletId: props.marker.id
        })
    },[props.marker])
    const handleChange = e =>  {
        setRatingData({
            ...ratingData,
            [e.target.name]: e.target.value
        })
    }
    const handleSubmit = e => {
        e.preventDefault()
       
        addRating(ratingData)
            .then(newMarker => {
                props.updateMarker(newMarker)
                props.changeView(newMarker)
            })
            .catch(error => {
                console.log(error)
                prompt(error.message)
            })
    }

    return(
        <>
        <div style={{display: props.displayMe}}>
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
            </>
    )
}
export default AddRatingPopup

