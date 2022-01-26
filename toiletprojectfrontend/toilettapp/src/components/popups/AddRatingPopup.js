import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { addRating } from "../../servercalls/Calls";
import Form from 'react-bootstrap/Form'

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
            <Form onSubmit={handleSubmit}>
                    Rating
                    <div key={`inline-radio`} className="mb-3">
                    <Form.Check
                        onChange={handleChange}
                        inline
                        label="1"
                        value="1"
                        name="rating"
                        type='radio'
                        id={`inline-radio-1`}
                    />
                    <Form.Check
                        onChange={handleChange}
                        inline
                        label="2"
                        value="2"
                        name="rating"
                        type='radio'
                        id={`inline-radio-2`}
                    />
                    <Form.Check
                        onChange={handleChange}
                        inline
                        label="3"
                        value="3"
                        name="rating"
                        type='radio'
                        id={`inline-radio-3`}
                    />
                    <Form.Check
                        onChange={handleChange}
                        inline
                        label="4"
                        value="4"
                        name="rating"
                        type='radio'
                        id={`inline-radio-4`}
                    />
                    <Form.Check
                        onChange={handleChange}
                        inline
                        label="5"
                        value="5"
                        name="rating"
                        type='radio'
                        id={`inline-radio-5`}
                    />
                    </div>
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
            </Form>
            </div>
            </>
    )
}
export default AddRatingPopup

