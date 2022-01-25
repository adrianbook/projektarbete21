import {useState} from "react";
import {getAllReports , getAllUserdefinedReports, getAllNonExistingToiletsReports , deleteToilet} from "../../servercalls/Calls";

const ShowReportsComponent = () => {
    const [reports, setReports] = useState([])
    const [showReports, setShowReports] = useState("none")
    const [showGetReportsButton, setShowGetReportsButton] = useState("block")
    function mapReport(report) {
        return {
            id: report.id,
            notes: report.issue,
            notAToilet: report.notAToilet.toString(),
            owningUserID: report.owningUser.id,
            owningUserName: report.owningUser.name,
            toiletId: report.toilet.id,
            toiletAvgRating: report.toilet.avgRating
        }
    }

    function getAllReportsClick() {
        getAllReports()
            .then(res => {
                res.map( report => {
                    reports.push(mapReport(report))
                })
                setShowGetReportsButton("none")
                setShowReports("block")
            })
    }
    function getAllUserdefinedReportsClick() {
        getAllUserdefinedReports()
            .then(res => {
                console.log(res)
                res.map( report => {
                    reports.push(mapReport(report))
                })
                setShowGetReportsButton("none")
                setShowReports("block")
            })
    }

    function getAllNonExistingToiletsClick() {
        getAllNonExistingToiletsReports()
            .then(res => {
                console.log(res)
                res.map( report => {
                    reports.push(mapReport(report))
                })
                setShowGetReportsButton("none")
                setShowReports("block")
            })
    }
    function hideReports() {
        setShowReports("none")
        setShowGetReportsButton("block")
        setReports([])

    }
    function deleteToiletClick(e) {
        let question = "Are you sure you want to delete this toilet, it also deletes all ratings and reports concerning this toilet"
        if (window.confirm(question)) {
            deleteToilet(e.target.value)
                .catch(err => {
                    alert(err.message)
                })
            let filtered = reports.filter(report => {
                return report.toiletId != e.target.value
            })
            console.log(filtered)
            setReports(filtered)
        } else {
            alert("Toilet not deleted")
        }
    }
    return (
        <div>
        <button style={{display: showGetReportsButton}} onClick={getAllReportsClick}>Get all reports</button>
        <button style={{display: showGetReportsButton}} onClick={getAllUserdefinedReportsClick}>Userdefined reports</button>
        <button style={{display: showGetReportsButton}} onClick={getAllNonExistingToiletsClick}>Nonexistant toilet reports</button>
        <div style={{display: showReports}}>
            {reports.map( report => (
                <div>
                <p>Report</p>
                <ul>
                    <li>id: {report.id}</li>
                    <li>notes: {report.notes}</li>
                    <li>no toilet: {report.notAToilet}</li>
                    <li>owning userID: {report.owningUserID}</li>
                    <li>owning username: {report.owningUserName}</li>
                    <li>toiletId: {report.toiletId}</li>
                    <li>toilet avg rating: {report.toiletAvgRating}</li>
                </ul>
                    <button value={report.toiletId} onClick={e => deleteToiletClick(e)}>Delete Toilet</button>
                </div>
                )
            )}
            <button onClick={hideReports}>Hide Reports</button>
        </div>
        </div>
    )
}

export default ShowReportsComponent