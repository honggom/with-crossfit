import styles from './ChildrenMenu.module.css';
import { Link } from 'react-router-dom';

export default function ChildrenMenu({ childrens }) {

    return (
        <>
            {childrens.map(children => (
                <Link to={children.link} key={children.title}>
                    <div className={styles.subMenu}>
                        <span>{children.title}</span>
                    </div>
                </Link>
            ))}
        </>
    );
}