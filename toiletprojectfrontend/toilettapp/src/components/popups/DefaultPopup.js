const DefaultPopup = (props) => {
    const handleChange = e => {
        props.changeView(e.target.name)
    }
    return (
        <>
        <div style={{display: props.displayMe}}>
        ID: {props.marker.id}
        <br />
        Average rating: {props.marker.avgRat}
        <br/>
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