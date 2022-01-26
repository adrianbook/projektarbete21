import {useEffect, useState} from "react";
import { sendReportToServer } from "../../servercalls/Calls";

const AddReportPopup = (props) => {
    const [formData, setFormData] = useState({
        issue: "",
        notAToilet: false,
        toiletId: props.marker.id
    })

    useEffect(()=>{
        setFormData({
            ...formData,
            toiletId: props.marker.id
        })
    },[props.marker])

    const handleSubmit = e => {
        e.preventDefault()
        e.target.reset()
        console.log(formData)
        sendReportToServer(formData)
            .then(response => {
                prompt(`report received on toilet number ${response.toilet.id}`)
                props.changeView()
            })
            .catch(e => console.log(e))
    }

    const handleChange = e => {
        if (e.target.type === "checkbox") {
            setFormData(prevState => ({
                ...prevState,
                [e.target.name]: !prevState[e.target.name]
            }))
        } else {
            setFormData({
                ...formData,
                [e.target.name]: e.target.value
            })
        }
    }
    return (
        <>
        <div style={{display: props.displayMe}}>
            <form onSubmit={handleSubmit}>
                <label>
                Issue:
                <input
                    type="text"
                    value={formData.issue}
                    name="issue"
                    onChange={handleChange}
                />
                </label>
                <br/>
                <label>
                Toilet does not exist
                <input
                    type="checkbox"
                    value={formData.notAToilet}
                    name="notAToilet"
                    onChange={handleChange}
                />
                </label>
                <br/>
                <button>submit</button>
            </form>
        </div>
        </>
    )
}

export default AddReportPopup