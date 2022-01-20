import { useEffect, useState } from "react"
import AddRatingPopup from "./AddRatingPopup"
import AddReportPopup from "./AddReportPopup"
import DefaultPopup from "./DefaultPopup"

const PopupContainer = (props) => {
    const marker = props.marker
    const noView = {
        default: "none",
        rating: "none",
        report: "none",
        create: "none"
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
        setView({
            ...noView,
            [newView]: ""
        })
    }


    return (
        <>
            <AddRatingPopup
                displayMe={view.rating}
                changeView={changeView}
                marker={props.marker}
            />
            <DefaultPopup
                displayMe={view.default}
                changeView={changeView}
                marker={props.marker}
            />
            <AddReportPopup
                displayMe={view.report}
                changeView={changeView}
                marker={props.marker}
            />
        </>
    )
}

export default PopupContainer