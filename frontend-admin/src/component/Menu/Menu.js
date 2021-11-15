import styles from './Menu.module.css';
import { GrFormDown } from 'react-icons/gr';
import { GrFormUp } from 'react-icons/gr';
import ChildrenMenu from './ChildrenMenu/ChildrenMenu';
import React, { useState } from 'react';

export default function Menu({ title, link, childrens }) {

    const [show, setShow] = useState(false);
    const [isUpbutton, setUpDown] = useState(false);

    if (childrens !== undefined) {
        return (
            <>
                <div className={styles.mainMenu}>
                    <span className={styles.mainMenuTitle}>{title}</span>

                    {!isUpbutton &&
                        (<GrFormDown className={styles.upDownButton}
                            onClick={() => {
                                setShow(!show);
                                setUpDown(!isUpbutton);
                            }}
                        />)}

                    {isUpbutton &&
                        (<GrFormUp className={styles.upDownButton}
                            onClick={() => {
                                setShow(!show);
                                setUpDown(!isUpbutton);
                            }}
                        />)}
                </div>

                {show && (<ChildrenMenu childrens={childrens} />)}
            </>
        );
    } else {
        return (
            <>
                <div className={styles.mainMenu}>
                    <span className={styles.mainMenuTitle}>{title}</span>
                </div>
            </>
        );
    }
}