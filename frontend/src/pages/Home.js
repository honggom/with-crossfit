export default function Home() {
    return (
        <div>
            <h3> Home </h3>
            <a href = {process.env.REACT_APP_BACK_END_BASE_URL}></a>
            <a href = "http://localhost:8080/api/hello">api test</a>
        </div>
    );
}