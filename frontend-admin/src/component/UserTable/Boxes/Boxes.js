export default function Boxes({ boxes }) {

    return (
        <>
            { 
                boxes.map(box => (
                    <option key={box.id} data-id={box.id} value={box.id}>{box.name}</option>
                ))
            }
        </>
    );
}