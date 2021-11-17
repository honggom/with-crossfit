import React, { useState, useEffect } from 'react';
import styles from './UserRegister.module.css';
import { BsSearch } from 'react-icons/bs';
import { IoMdRefresh } from 'react-icons/io';
import UserTable from '../../component/UserTable/UserTable';

export default function UserRegister() {

    // TODO 검색 기능 구현

    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <p>회원 등록</p>
            </div>

            <div className={styles.middleWrapper}>

                <div className={styles.searchDetailsWrapper}>

                    <div className={styles.searchDetail}>
                        <div>
                            <span>이메일 : </span>
                            <input className={styles.inputStyle}
                                type={'text'}
                            ></input>
                        </div>

                        <div>
                            <span>이름 : </span>
                            <input className={styles.inputStyle}
                                type={'text'}
                            ></input>
                        </div>
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
                <table>
                    <thead>
                        <tr>
                            <th>이메일</th>
                            <th>이름</th>
                            <th>가입일자</th>
                            <th>등록</th>
                        </tr>
                    </thead>
                    <UserTable />
                </table>
            </div>

        </div>
    );
}