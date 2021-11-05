export default function Login() {
    
    // TODO 이미 로그인이 되어 있다면 HOME으로

    const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;
    
    return (
        <div>
            <a href={backEndBaseUri + "/oauth2/authorization/google"}>구글 로그인</a>
        </div>
    );
}