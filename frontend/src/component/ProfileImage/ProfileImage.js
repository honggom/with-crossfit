import styles from './ProfileImage.module.css';
import { FaRegUserCircle } from "react-icons/fa";

export default function ProfileImage() {

    return (
        <div className={styles.wrapper}>
            <FaRegUserCircle className={styles.userIcon}/>
        </div>
    );
}