import { useEffect, useState } from "react"
import AddRatingPopup from "./AddRatingPopup"
import AddReportPopup from "./AddReportPopup"
import AddToiletPopup from "./AddToiletPopup"
import DefaultPopup from "./DefaultPopup"
import SetAddressPopup from "./SetAddressPopup"

const PopupContainer = (props) => {
    const [marker, setMarker] = useState(props.marker)

    const noView = {
        default: "none",
        rating: "none",
        report: "none",
        toilet: "none"
        }
    const [view, setView] = useState({
        ...noView,
        default: ""
    })

    useEffect(() => {
        if (Object.getOwnPropertyNames(view).includes(props.type)) {
            setView({
                ...noView,
                [props.type]: ""
            })
        }
    },[])

    const changeView = (newView) => {
        const viewToSet = newView || "default"
        setView({
            ...noView,
            [viewToSet]: ""
        })
    }

    const updateMarker = marker => {
        props.addMarker(marker)
        setMarker(marker)
    }


    return (
        <>
            <SetAddressPopup 
                marker={marker}

            />
            <AddRatingPopup
                displayMe={view.rating}
                changeView={changeView}
                marker={marker}
                updateMarker={updateMarker}
            />
            <DefaultPopup
                displayMe={view.default}
                changeView={changeView}
                marker={marker}
            />
            <AddReportPopup
                displayMe={view.report}
                changeView={changeView}
                marker={marker}
            />
            <AddToiletPopup
                displayMe={view.toilet}
                changeView={changeView}
                marker={marker}
                updateMarker={updateMarker}
            />
        </>
    )
}

export default PopupContainer