import styles from './WodHistory.module.css';
import React, { useState, useEffect } from 'react';
import { getWod } from '../../api/pages/WodHistory';
import { useNavigate, Link } from "react-router-dom";
import { errorHandle } from '../../util/util';
import Numbers from '../../component/Numbers/Numbers';
import numbersStyle from '../../component/Numbers/NumbersDefaultStyle';

export default function WodHistory() {

    let navigate = useNavigate();

    let size = 10;
    let sort = 'createdAt,DESC';
    let maxPageShow = 10;

    const [page, setPage] = useState(0);
    const [startPage, setStartPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [wods, setWods] = useState([]);

    useEffect(() => {
        setStatus(page);
        setGrid(size, page, sort);
    }, [page, totalPages]);

    function setGrid(size, page, sort) {
        getWod(size, page, sort).then((response) => {
            setWods(response.data.content);
            setTotalPages(response.data.totalPages);
        }).catch((error) => {
            errorHandle(error, navigate);
        });
    }

    function setStatus(clickedPage) {
        setPage(clickedPage);
        setStartPage((Math.ceil((clickedPage + 1) / maxPageShow) - 1) * maxPageShow + 1);
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
                    <tbody className={styles.tbody}>
                        {wods.map(wod => (
                            <tr key={wod.id}>
                                <td>
                                    <Link to={`/read-wod/${wod.id}`}>
                                        <span className={styles.wodTitle}>{wod.title}</span>
                                    </Link>
                                </td>
                                <td><span>{wod.writer.name}</span></td>
                                <td><span>{wod.createdAt}</span></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <div className={styles.bottomWrapper}>
                <Numbers startPage={startPage}
                    maxPageShow={maxPageShow}
                    totalPages={totalPages}
                    style={numbersStyle}
                    setPage={setPage}
                    page={page}
                />
            </div>
        </div>
    );

}