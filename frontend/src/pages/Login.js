export default function Login() {
    
    const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;
    
    return (
        <div>
            <a href={backEndBaseUri + "/oauth2/authorization/google"}>구글 로그인</a>
            <div>
            <a href={backEndBaseUri + "/api/hello"}>api test</a>
            </div>
        </div>
    );
}