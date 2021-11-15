import React, { useState, useEffect } from 'react';
import { getBox } from '../../api/pages/UserRegister';
import { useNavigate } from "react-router-dom";
import { ifExpired } from '../../util/util';
import styles from './UserRegister.module.css';
import { BsSearch } from 'react-icons/bs';
import { IoMdRefresh } from 'react-icons/io';

export default function UserRegister() {

    let navigate = useNavigate();

    useEffect(() => {
        getBox().then((response) => {
            ifExpired(response, navigate);
            console.log(response.data);
        }).catch((error) => {
            // TODO 예외처리
        });
    }, []);

    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <p>회원 등록</p>
            </div>

            <div className={styles.middleWrapper}>

                <div className={styles.searchDetailsWrapper}>

                    <div className={styles.searchDetail}>
                        <div>
                            <span>이름 : </span>
                            <input className={styles.inputStyle}
                                   type={'text'}
                            ></input>
                        </div>

                        <div>
                            <span>등급 : </span>
                            <input className={styles.inputStyle}
                                   type={'text'}
                            ></input>
                        </div>
                        <div>
                            <span>지점 : </span>
                            <input className={styles.inputStyle}
                                   type={'text'}
                            ></input>
                        </div>
                    </div>

                    <div className={styles.searchDetail}>
                        <div>ex 1</div>
                        <div>ex 2</div>
                        <div>ex 3</div>
                    </div>

                    <div className={styles.buttonWrapper}>
                        <button className={styles.button}>
                            <BsSearch />
                        </button>
                        <button className={styles.button}>
                            <IoMdRefresh />
                        </button>
                    </div>

                </div>

            </div>

            <div className={styles.bottomWrapper}>
                a
            </div>

        </div>
    );
}