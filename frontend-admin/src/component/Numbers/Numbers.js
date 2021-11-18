export default function Numbers({ startPage, maxPageShow, totalPages, style, setPage, page }) {
    const cumstomStyle = style.numbersStyle;

    let left = startPage > maxPageShow;
    let right = totalPages > startPage + maxPageShow - 1;
    const arr = [];

    for (let i = startPage; i < startPage + maxPageShow; i++) {
        if (i <= totalPages) {
            if (page + 1 === i) {
                arr.push(<div key={i} style={cumstomStyle.curNumber} onClick={(item) => { setPage(Number(item.target.innerText) - 1); }}>{i}</div>);
            } else {
                arr.push(<div key={i} style={cumstomStyle.number} onClick={(item) => { setPage(Number(item.target.innerText) - 1); }}>{i}</div>);
            }
        }
    }

    return (
        <>
            <div style={cumstomStyle.leftButtonWrapper}>
                {left && (<div onClick={() => {
                    setPage(startPage - 2);
                }}>{'<'}</div>)}
            </div>

            <div style={cumstomStyle.numbersWarpper}>
                {arr}
            </div>

            <div style={cumstomStyle.rightButtonWrapper}>
                {right && (<div onClick={() => { setPage((startPage - 1) + maxPageShow); }}>{'>'}</div>)}
            </div>
        </>
    );
}