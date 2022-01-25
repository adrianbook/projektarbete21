import { sendNewToiletToServer } from "../../servercalls/Calls"
import {useEffect, useState} from "react";

const AddToiletPopup = (props) => {
    const [formData, setFormData] = useState({
        urinal: false,
        separateGenders: false,
        changingTable: false,
        shower: false,
        handicapFriendly: false
    })

    let descriptions = {
        urinal: "Urinal",
        separateGenders: "Separate Genders",
        changingTable: "Changing Table",
        shower: "Shower",
        handicapFriendly: "Handicap Friendly"
    }


    const addToilet = () => {
        sendNewToiletToServer({latitude: props.marker.thispos[0],
                                        longitude:  props.marker.thispos[1],
                                            ...formData})
        .then(marker => {
            props.updateMarker(marker)
            props.changeMarker(marker)
        })
        .catch(e => {
            prompt(e.message)
        })
    }
    const handleChange = e => {
            setFormData(prevState => ({
                ...prevState,
                [e.target.name]: !prevState[e.target.name]
            }))
        }




    return(
        <>
        <div style={{display: props.displayMe}}>
            <form>
                {Object.getOwnPropertyNames(descriptions).map(des => {
                    return (
                        <div>
                        <label> {descriptions[des]}
                            <input
                                onChange={handleChange}
                                name={des}
                                value={formData[des]}
                                type="checkbox"
                            />
                        </label><br/>
                        </div>)
                })}

            </form>
            <button onClick={addToilet}>
                Add Toilet here
            </button>
        </div>
        </>
    )
}

export default AddToiletPopup