import {marker} from "leaflet/dist/leaflet-src.esm";

const DefaultPopup = (props) => {
    const handleChange = e => {
        props.changeView(e.target.name)
    }
     let descriptions = {
         urinal: "Urinal",
         separateGenders: "Separate Genders",
         changingTable: "Changing Table",
         shower: "Shower",
         handicapFriendly: "Handicap Friendly"
     }


    let avgRating = props.marker.avgRat === 0? "Not rated yet" : props.marker.avgRat
    return (
        <>
        <div style={{display: props.displayMe}}>
        ID: {props.marker.id}
        <br />
        Average rating: {avgRating}
        <br/>
            <ul>
                <p>Has:</p>
            {Object.getOwnPropertyNames(props.marker).map(o => {
                if(props.marker[o] && descriptions[o]) {
                    return <li>{descriptions[o]}</li>
                }
            })}
            </ul>
        <button name="rating" onClick={handleChange}>
            Rate toilet
        </button>
        <button name="report" onClick={handleChange}>
            Report toilet
        </button>
        </div>
        </>
    )
}

export default DefaultPopup