import { useState } from "react";

const AddReportPopup = (props) => {
    const [formData, setFormData] = useState({
        issue: "",
        notAToilet: false,
        toiletId: props.marker.id
    })

    const handleSubmit = e => {
        e.preventDefault()

        console.log(`send report to server:\nnotAtoil: ${formData.notAToilet}\nid: ${formData.toiletId}\nissue: ${formData.issue}`)
        props.changeView("default")
    }

    const handleChange = e => {
        console.log(e.target.type)
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