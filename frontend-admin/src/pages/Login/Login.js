import styles from './Login.module.css';
import { FcGoogle } from 'react-icons/fc';

export default function Login() {

    // TODO 디자인

    const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;
    
    return (
        <div className={styles.wrapper}>
            <div className={styles.loginWrapper}>
                <FcGoogle classNam={styles.googleIcon}/>
                <a href={backEndBaseUri + "/oauth2/authorization/google"}>
                    <button className={styles.loginButton}>구글 로그인</button>
                </a>
            </div>
        </div>
    );
}