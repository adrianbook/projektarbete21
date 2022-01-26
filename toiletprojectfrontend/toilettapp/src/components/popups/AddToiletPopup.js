import { sendNewToiletToServer } from "../../servercalls/Calls"
import {useEffect, useState} from "react";

const AddToiletPopup = (props) => {

    const emptyForm = {
        cost: false,
        urinal: false,
        separateGenders: false,
        changingTable: false,
        shower: false,
        handicapFriendly: false
    }
    const [formData, setFormData] = useState(emptyForm)

    const changeView = props.changeView
    let position = props.marker

    useEffect(() => {
        if (!Object.getOwnPropertyNames(position).includes("id")) {
            position.id = 0
            setFormData({
                cost: false,
                urinal: false,
                separateGenders: false,
                changingTable: false,
                shower: false,
                handicapFriendly: false
            })
            changeView("toilet")
        }
    },[position, changeView, setFormData])
    

    let descriptions = {
        cost: "Not free",
        urinal: "Urinal",
        separateGenders: "Separate Genders",
        changingTable: "Changing Table",
        shower: "Shower",
        handicapFriendly: "Handicap Friendly"
    }

  

    const addToilet = (e) => {
        e.preventDefault()
        e.target.reset()
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
            <form onSubmit={addToilet}>
                {Object.getOwnPropertyNames(descriptions).map(des => {
                    return (
                        <div key={des}>
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

            <button>
                Add Toilet here
            </button>
            </form>
        </div>
        </>
    )
}

export default AddToiletPopup