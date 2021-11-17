import styles from './WodHistory.module.css';
import React, { useState, useEffect } from 'react';
import { getWod } from '../../api/pages/WodHistory';
import { useNavigate } from "react-router-dom";
import { errorHandle } from '../../util/util';

export default function WodHistory() {

    let navigate = useNavigate();

    let page = 0;
    const size = 10;
    const sort = 'date,DESC';
    const maxPageShow = 10;

    let startPage = Math.ceil(page + 1 / maxPageShow);
    let totalPages = 0;

    const [left, setLeft] = useState(page > maxPageShow - 1);
    const [right, setRight] = useState(totalPages - maxPageShow >= page);
    const [wods, setWods] = useState([]);

    const [status, setStatus] = useState({start: start, maxPageShow: maxPageShow, totalPages: totalPages});

    useEffect(() => {
        setGrid(size, page, sort);
    }, []);

    function setGrid(size, page, sort) {
        getWod(size, page, sort).then((response) => {
            setWods(response.data.content);
            totalPages = response.data.totalPages;
        }).catch((error) => {
            errorHandle(error, navigate);
        });
    }


    // TODO numbers ==> 컴포넌트로 바꾸고 status 전달

    function numbers(start) {
        const arr = [];

        for (let i = start; i < start + maxPageShow; i++) {
            if (i <= totalPages) {
                arr.push(<div key={i}
                    className={styles.number}
                    onClick={(item) => {
                    }}
                >
                    {i}
                </div>);
            }
        }
        return arr;
    }

    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <p>WOD 이력</p>
            </div>

            <div className={styles.middleWrapper}>
                <table>
                    <thead>
                        <tr>
                            <th className={styles.th1}>제목</th>
                            <th className={styles.th2}>작성자</th>
                            <th className={styles.th3}>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        {wods.map(wod => (
                            <tr key={wod.date}>
                                <td>{wod.title}</td>
                                <td>{wod.writer.name}</td>
                                <td>{wod.date}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <div className={styles.bottomWrapper}>

                <div className={styles.leftButtonWrapper}>
                    {left && ('<')}
                </div>

                <div className={styles.numbersWarpper}>
                    {numbers(startPage)}
                </div>

                <div className={styles.rightButtonWrapper}>
                    {right && ('>')}
                </div>

            </div>
        </div>
    );

}